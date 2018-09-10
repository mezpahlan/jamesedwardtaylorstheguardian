package uk.co.mezpahlan.oldtimerag.theguardian.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.koin.test.declareMock
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.junit.MockitoJUnitRunner
import uk.co.mezpahlan.oldtimerag.base.LceType
import uk.co.mezpahlan.oldtimerag.theguardian.article.Article
import uk.co.mezpahlan.oldtimerag.theguardian.data.TheGuardianRepository
import uk.co.mezpahlan.oldtimerag.theguardian.di.appModules
import uk.co.mezpahlan.oldtimerag.theguardian.feed.FeedItem
import uk.co.mezpahlan.oldtimerag.theguardian.feed.FeedType

@RunWith(MockitoJUnitRunner::class)
class SharedViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        startKoin(appModules)
        declareMock<TheGuardianRepository>()
    }

    @Test
    fun should_ShowContent_When_FetchFeedCompletes() {
        // Given
        val sharedViewModel: SharedViewModel by inject()
        val repository: TheGuardianRepository by inject()
        given(repository.fetchFeed(FeedType.ALL)).willReturn(Single.just(emptyList()))

        // When
        sharedViewModel.loadFeed()

        // Then
        assert(sharedViewModel.lceType.value == LceType.CONTENT)
    }

    @Test
    fun should_ShowContent_When_FetchArticleCompletes() {
        // Given
        val sharedViewModel: SharedViewModel by inject()
        val repository: TheGuardianRepository by inject()
        sharedViewModel.articleId.value = "article_id"
        given(repository.fetchArticle(anyString())).willReturn(Single.just(Article("id", "title", "page_data")))

        // When
        sharedViewModel.loadArticle()

        // Then
        assert(sharedViewModel.lceType.value == LceType.CONTENT)
    }

    @Test
    fun should_SetFeed_When_FetchFeedCompletes() {
        // Given
        val sharedViewModel: SharedViewModel by inject()
        val repository: TheGuardianRepository by inject()
        given(repository.fetchFeed(FeedType.ALL)).willReturn(Single.just(emptyList()))

        // When
        sharedViewModel.loadFeed()

        // Then
        assert(sharedViewModel.items.value == emptyList<FeedItem>())
    }

    @Test
    fun should_SetArticle_When_FetchArticleCompletes() {
        // Given
        val sharedViewModel: SharedViewModel by inject()
        val repository: TheGuardianRepository by inject()
        sharedViewModel.articleId.value = "article_id"
        given(repository.fetchArticle(anyString())).willReturn(Single.just(Article("id", "title", "page_data")))

        // When
        sharedViewModel.loadArticle()

        // Then
        assert(sharedViewModel.article.value == Article("id", "title", "page_data"))
    }

    @Test
    fun should_ShowError_When_FetchFeedFails() {
        // Given
        val sharedViewModel: SharedViewModel by inject()
        val repository: TheGuardianRepository by inject()
        given(repository.fetchFeed(FeedType.ALL)).willReturn(Single.error(Throwable("KABOOM!")))

        // When
        sharedViewModel.loadFeed()

        // Then
        assert(sharedViewModel.lceType.value == LceType.ERROR)
    }

    @Test
    fun should_ShowError_When_FetchArticleFails() {
        // Given
        val sharedViewModel: SharedViewModel by inject()
        val repository: TheGuardianRepository by inject()
        sharedViewModel.articleId.value = "article_id"
        given(repository.fetchArticle(anyString())).willReturn(Single.error(Throwable("KABOOM!")))

        // When
        sharedViewModel.loadArticle()

        // Then
        assert(sharedViewModel.lceType.value == LceType.ERROR)
    }
}