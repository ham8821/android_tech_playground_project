package nz.co.test.transactions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import nz.co.test.transactions.infrastructure.model.Transaction
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.BDDMockito.given
import java.lang.Exception


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TransactionListViewModelTest {

    lateinit var viewModel: TaskViewModel

    @Mock
    private lateinit var repository: TransactionsRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = TaskViewModel(repository)
    }

    @Test
    fun onRetrieve_transaction_success_showTransaction() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            Mockito.`when`(repository.retrieveTransactions()).thenReturn(
                viewModelData
            )
            viewModel.retrieveTransactions()
            assert(viewModel.showTransactions.value!! == viewModelData)
        }
    }

    @Test
    fun onRetrieve_transaction_empty_response_showErrorView() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            Mockito.`when`(repository.retrieveTransactions()).thenReturn(
                emptyResponse
            )
            viewModel.retrieveTransactions()
            assert(viewModel.isLoading.value == false)
            assert((viewModel.showTransactions.value).isNullOrEmpty())
        }
    }

    @Test
    fun onRetrieve_transaction_error_showErrorView() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            given(repository.retrieveTransactions()).willAnswer { throw Exception() }

            viewModel.retrieveTransactions()
            assert(viewModel.isLoading.value == false)
            assert((viewModel.showTransactions.value).isNullOrEmpty())
            assert(viewModel.showNoTransactonFoundView.value == true)
        }
    }

    @ExperimentalCoroutinesApi
    class MainCoroutineRule(
        val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    ) : TestWatcher() {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }

    private val viewModelData =
        ArrayList<Transaction>().apply {
            add(Transaction(1,"2022-02-07","summary", "debit", "credit"))
            add(Transaction(1,"2021-02-05","summary", "debit", "credit"))
            add(Transaction(1,"2023-02-05","summary", "debit", "credit"))
        }

    private val emptyResponse = ArrayList<Transaction>()
}