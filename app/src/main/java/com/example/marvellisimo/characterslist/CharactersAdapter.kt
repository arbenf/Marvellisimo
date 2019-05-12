package com.example.marvellisimo.characterslist

import android.arch.paging.PagedListAdapter
import android.content.Intent
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvellisimo.R
import com.example.marvellisimo.extensions.load
import com.example.marvellisimo.api.entity.Character
import kotlinx.android.synthetic.main.activity_character_info.view.*
import kotlinx.android.synthetic.main.item_character.view.*

class CharactersAdapter : PagedListAdapter<Character, CharactersAdapter.VH>(characterDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val character = getItem(position)
        holder.itemView.txtName.text = character?.name
        holder.itemView.imgThumbnail.load("${character?.thumbnail?.path}/standard_medium.${character?.thumbnail?.extension}")

        holder.character = character
    }

    class VH(itemView: View, var character: Character? = null) : RecyclerView.ViewHolder(itemView) {
//        val imgThumbnail = itemView.imgThumbnail
//        val txtName = itemView.txtNam

        companion object {
            val CHARACTER_NAME = "CHARACTER_NAME"
            val CHARACTER_DESCRIPTION = "CHARACTER_DESCRIPTION"
        }

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, CharacterInfoActivity::class.java)

                intent.putExtra(CHARACTER_NAME, character?.name)
                intent.putExtra(CHARACTER_DESCRIPTION, character?.description)

                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val characterDiff = object: DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(old: Character, new: Character): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: Character, new: Character): Boolean {
                return old == new
            }

        }
    }
}