package com.example.testapplication1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class TestFragment : Fragment() {

    private val viewModel by viewModels<TestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = context ?: return null
        val version = arguments?.getInt(KEY) ?: 0
        return ComposeView(context).apply {
            when (version) {
                0 -> {
                    setViewCompositionStrategy(ViewCompositionStrategy.Default)
//                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                }

                1 -> {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                        override fun onViewAttachedToWindow(v: View) = Unit
                        override fun onViewDetachedFromWindow(v: View) {
                            disposeComposition()
                        }
                    })
                }

                else -> {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                        override fun onViewAttachedToWindow(v: View) = Unit
                        override fun onViewDetachedFromWindow(v: View) {
                            disposeComposition()
                            removeAllViews()
                        }
                    })
                }
            }
            test(viewModel)
        }
    }

    companion object {
        private const val KEY = "key.test.fragment.version"
        fun newInstance(version: Int): Fragment = TestFragment().apply {
            arguments = Bundle().apply { putInt(KEY, version) }
        }
    }
}
