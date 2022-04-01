package u05lab.ex2

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.math.Ordered.orderingToOrdered

enum Question:
  case RELEVANCE
  case SIGNIFICANCE
  case CONFIDENCE
  case FINAL

trait ConferenceReviewing:
  def loadReview(article: Int, scores: Map[Question, Int]): Unit
  def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, fin: Int): Unit
  def orderedScores(article: Int, question: Question): List[Int]
  def averageFinlScore(article: Int): Double
  def acceptedArticles(): Set[Int]
  def sortedAcceptedArticles(): List[(Int, Double)]
  def averageWeightedFinalScoreMap(): Map[Int, Double]

object  ConferenceReviewing:
  def apply(): ConferenceReviewing =  ConferenceReviewingImpl()

  private class ConferenceReviewingImpl extends ConferenceReviewing:
    private val reviews: ListBuffer[(Int, Map[Question, Int])] = ListBuffer[(Int, Map[Question, Int])]()

    override def loadReview(article: Int, scores: Map[Question, Int]): Unit =
      if scores.size < Question.values.length then new IllegalArgumentException else reviews += ((article, scores))

    override def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, fin: Int): Unit =
      val map: Map[Question, Int] = Map[Question, Int](Question.RELEVANCE -> relevance, Question.SIGNIFICANCE -> significance, Question.CONFIDENCE -> confidence, Question.FINAL -> fin)
      reviews += ((article, map))

    override def orderedScores(article: Int, question: Question): List[Int] =
      reviews.view.filter(p => p._1 == article).map(p => p._2.get(question).get).toList.sorted

    override def averageFinlScore(article: Int): Double =
      reviews.view.filter(p => p._1 == article).map(p => p._2.get(Question.FINAL).get).foldLeft((0.0, 1))((acc, elem) => ((acc._1 + elem) / acc._2, acc._2 + 1))._1

    private def accepted(article: Int): Boolean =
      reviews.view.filter(p => p._1 == article).map(p => p._2).exists(p => p.get(Question.RELEVANCE).get >= 8)

    override def acceptedArticles(): Set[Int] =
      reviews.view.map(p => p._1).distinct.filter(p => this.averageFinlScore(p) >= 5.0).filter(p => this.accepted(p)).toSet

    override def sortedAcceptedArticles(): List[(Int, Double)] =
      this.acceptedArticles().view.map(p => (p, this.averageFinlScore(p))).toList.sorted((p1, p2) => p1._2.compareTo(p2._2))

    private def averageWeightedFinalScoreArticle(article: Int): Double =
      reviews.view.filter(p => p._1 == article).map(p => p._2.get(Question.FINAL).get * p._2.get(Question.CONFIDENCE).get / 10.0).foldLeft((0.0, 1))((acc, elem) => ((acc._1 + elem) / acc._2, acc._2 + 1))._1

    override def averageWeightedFinalScoreMap(): Map[Int, Double] =
      reviews.view.map(p => p._1).distinct.map(p => (p, this.averageWeightedFinalScoreArticle(p))).toMap

