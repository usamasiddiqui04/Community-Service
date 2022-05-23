package com.bytee.communityservice.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bytee.communityservice.R
import com.bytee.communityservice.model.Handicap

class HandicapAdapter(private val title: ArrayList<Handicap>) :
    RecyclerView.Adapter<HandicapAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.tv_title)
//            itemView.setOnClickListener {
//                onItemClick?.invoke(contacts[adapterPosition])
//            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_generic, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = title[position].ngoName

        viewHolder.textView.setOnClickListener {
            try {
                val bundle = Bundle()
                bundle.putParcelable("handicap", title[position])
                it.findNavController().navigate(R.id.action_joinHandyCappedEventListFragment_to_joinHandyCappedDetailFragment , bundle)
            }catch (e : Exception){
                val bundle = Bundle()
                bundle.putParcelable("handicap", title[position])
                it.findNavController().navigate(R.id.action_availableWheelChairListFragment_to_joinHandyCappedDetailFragment , bundle)
            }

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = title.size

}
