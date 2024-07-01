package com.example.safety

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safety.databinding.ItemInviteMailBinding

class InviteMailAdapter(
    private val listInvites: List<String>,
    private val onActionClick: ProfileFragment
) : RecyclerView.Adapter<InviteMailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InviteMailAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = ItemInviteMailBinding.inflate(inflater, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: InviteMailAdapter.ViewHolder, position: Int) {
        val item = listInvites[position]
        holder.name.text = item

        holder.deny.setOnClickListener {
            onActionClick.onDenyClick(item)
        }
    }

    override fun getItemCount(): Int {
        return listInvites.size
    }

    class ViewHolder(private val item: ItemInviteMailBinding) : RecyclerView.ViewHolder(item.root) {
        val name = item.mail
        val deny = item.deny
    }

    interface OnActionClick {
        fun onDenyClick(mail: String)
    }
}
