package br.com.leandro.podcast.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leandro.podcast.R
import br.com.leandro.podcast.core.database.AppDatabase
import br.com.leandro.podcast.core.repository.HistoryRepositoryImpl
import br.com.leandro.podcast.databinding.FragmentHomeBinding
import br.com.leandro.podcast.domain.GetHistoriesUseCaseImpl
import br.com.leandro.podcast.model.HistoryItem
import br.com.leandro.podcast.utils.hideKeyboard

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        val db = AppDatabase.getInstance(requireContext())
        val historyRepository = HistoryRepositoryImpl(db)
        val getHistoriesUseCase = GetHistoriesUseCaseImpl(historyRepository = historyRepository)
        HomeViewModel.Factory(getHistoriesUseCase = getHistoriesUseCase)
    }
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(HistoryListLifecycleObserver(viewModel))
        adapter = HistoryAdapter { history ->
            navigateToDetails(history)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observer UI State for changes.
        viewModel.stateOnceAndStream().observe(viewLifecycleOwner) {
            bindUiState(it)

            binding.textInput.text?.clear()
            binding.textInput.clearFocus()
            binding.textInput.hideKeyboard()
        }

        // Set TextInput Action Listener
        binding.textInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.checkRssLink(binding.textInput.text.toString())
            } else false
        }

        binding.imageViewDeleteAll.setOnClickListener {
            onDeleteButtonClicked()
        }
    }

    private fun navigateToDetails(history: HistoryItem) {
        val bundle = Bundle().apply {
            putSerializable("history", history)
        }

        findNavController().navigate(R.id.action_navigation_home_to_navigation_details, bundle)
    }

    /**
     * Delete all histories.
     *
     * Show a dialog to confirm the action.
     */
    private fun onDeleteButtonClicked() {
        val alert = Dialog(requireContext(), R.style.dialogTheme)
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alert.setContentView(R.layout.delete_dialog_view)
        activity?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        val buttonYes: Button = alert.findViewById(R.id.buttonYes)
        val buttonNo: Button = alert.findViewById(R.id.buttonNo)

        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.show()

        buttonYes.setOnClickListener {
            alert.dismiss()

            viewModel.deleteAllHistory()
        }
        buttonNo.setOnClickListener { alert.dismiss() }
    }

    /**
     * Bind UI State to View.
     *
     * Update list of histories according to updates.
     */
    private fun bindUiState(uiState: HomeViewModel.UiState) {
        adapter.updateHistories(uiState.historyItemList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}