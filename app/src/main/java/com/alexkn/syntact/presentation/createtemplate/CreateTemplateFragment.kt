package com.alexkn.syntact.presentation.createtemplate

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.alexkn.syntact.R
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.presentation.createbucket.CreateBucketViewModel
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.create_template_fragment.*

class CreateTemplateFragment : Fragment() {

    private lateinit var viewModel: CreateTemplateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext());

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.create_template_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, (activity!!.application as ApplicationComponentProvider).applicationComponent.createTemplateViewModelFactory())
                .get(CreateTemplateViewModel::class.java)


        createTemplateButton.setOnClickListener{
            var text = editText.text.toString().trim()
            viewModel.createFromText(text)
        }

    }


}
