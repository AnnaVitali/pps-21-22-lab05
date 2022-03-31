package u05lab.ex2

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

enum Question:
  case RELEVANCE
  case SIGNIFICANCE
  case CONFIDENCE
  case FINAL

trait ConferenceReviewing:
  def reviews: ListBuffer[(Int, Map[Question, Int])]
  def loadReview(article: Int, scores: Map[Question, Int]): Unit
  def loadReview(article: Int, relevance: Int, confidence: Int, fin: Int): Unit
  def orderedScores(article: Int, question: Question): List[Int]
  def averageFinlScore(article: Int): Double
  def acceptedArticles(): Set[Int]
  def sortedAcceptedArticles(): List[(Int, Double)]
  def averageWeightedFinalScoreMap(): Map[Int, Double]

object  ConferenceReviewing:
  def apply(reviews: ListBuffer[(Int, Map[Question, Int])]): ConferenceReviewing = ???

  private class ConferenceReviewingImpl(override val reviews: ListBuffer[(Int, Map[Question, Int])]) extends ConferenceReviewing:
    def loadReview(article: Int, scores: Map[Question, Int]): Unit =
      if scores.size < Question.values.length then new IllegalArgumentException else reviews += ((article, scores))

    def loadReview(article: Int, relevance: Int, confidence: Int, fin: Int): Unit =
      val map: mutable.Map[Question, Int] = mutable.Map[Question, Int]()


    def orderedScores(article: Int, question: Question): List[Int] = ???
    def averageFinlScore(article: Int): Double = ???
    def acceptedArticles(): Set[Int] = ???
    def sortedAcceptedArticles(): List[(Int, Double)] = ???
    def averageWeightedFinalScoreMap(): Map[Int, Double] = ???

