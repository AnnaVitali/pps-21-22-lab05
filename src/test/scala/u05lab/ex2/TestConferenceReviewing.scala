package u05lab.ex2

import org.junit.{Before, Test}
import org.junit.Assert.*

class TestConferenceReviewing {

  val cr: ConferenceReviewing = ConferenceReviewing.apply();

  @Before
  def init(): Unit =
    cr.loadReview(1, 8, 8, 6, 8) // 4.8 è il voto finale pesato (usato da averageWeightedFinalScoreMap)
    cr.loadReview(1, 9, 9, 6, 9) // 5.4
    cr.loadReview(2, 9, 9, 10, 9) // 9.0
    cr.loadReview(2, 4, 6, 10, 6) // 6.0
    cr.loadReview(3, 3, 3, 3, 3) // 0.9
    cr.loadReview(3, 4, 4, 4, 4) // 1.6
    cr.loadReview(4, 6, 6, 6, 6) // 3.6
    cr.loadReview(4, 7, 7, 8, 7) // 5.6

    cr.loadReview(4, Map(Question.RELEVANCE-> 8, Question.SIGNIFICANCE-> 8, Question.CONFIDENCE -> 7, Question.FINAL -> 7))
    cr.loadReview(5, 6, 6, 6, 10)
    cr.loadReview(5, 7, 7, 7, 10) // 7.0


  @Test
  def testOrderedScore(): Unit =
    assertEquals(List(4,9), cr.orderedScores(2, Question.RELEVANCE))

  @Test
  def testAvarageFInalScore(): Unit =
    assertEquals(8.5, cr.averageFinlScore(1), 0.01)

  @Test
  def testAcceptedArticles(): Unit =
    assertEquals(Set(1,2), cr.acceptedArticles());

  @Test
  def testSortedAcceptedArticles(): Unit =
    assertEquals(List( (2,7.5),(1,8.5)), cr.sortedAcceptedArticles());

  @Test
  def testAverageWeightedFinalScore(): Unit =
    assertEquals((4.8+5.4)/2, cr.averageWeightedFinalScoreMap().get(1).get, 0.01);

}
