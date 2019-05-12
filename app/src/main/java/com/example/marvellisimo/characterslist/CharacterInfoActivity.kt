package com.example.marvellisimo.characterslist

import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedListAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvellisimo.R
import com.example.marvellisimo.api.entity.Character
import com.example.marvellisimo.extensions.load
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_character_info.*
import kotlinx.android.synthetic.main.activity_character_info.view.*
import kotlinx.android.synthetic.main.activity_characters.*
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterInfoActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharacterInfoActivityAdapter by lazy {
        CharacterInfoActivityAdapter()
    }

    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_characters)

        recyclerCharacters.setBackgroundColor(Color.GRAY)
        recyclerCharacters.layoutManager = LinearLayoutManager(this)
        recyclerCharacters.adapter = adapter


        val navBarTitle = intent.getStringExtra(CharactersAdapter.VH.CHARACTER_NAME)
        supportActionBar?.title = navBarTitle

        val charDescription = intent.getStringExtra(CharactersAdapter.VH.CHARACTER_DESCRIPTION)
        charInfotextView?.text = charDescription
       // subscribeToList()
    }

    private fun subscribeToList() {
        val disposable = viewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    adapter.submitList(list)
                    if (recyclerState != null) {
                        recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null
                    }
                },
                { e ->
                    Log.e("NGVL", "Error", e)
                }
            )
    }

    private class CharacterInfoActivityAdapter :
        PagedListAdapter<Character, CharacterInfoActivityAdapter.VH>(characterDiff) {

        override fun getItemCount(): Int {
            return 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//            val blueView = View(parent.context)
//            blueView.setBackgroundColor(Color.BLACK)
//            blueView.minimumHeight = 50
//            return VH(blueView)
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_character_info, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
//            val character = getItem(position)
//            holder.itemView.charInfotextView.text = character?.name
//            holder.itemView.charInfoimageView.load("${character?.thumbnail?.path}/standard_medium.${character?.thumbnail?.extension}")
        }

        class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            //        val imgThumbnail = itemView.imgThumbnail
//        val txtName = itemView.txtName
//            init {
//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, CharacterInfoActivity::class.java)
//                    itemView.context.startActivity(intent)
//                }
//            }
        }

        companion object {
            val characterDiff = object : DiffUtil.ItemCallback<Character>() {
                override fun areItemsTheSame(old: Character, new: Character): Boolean {
                    return old.id == new.id

                }

                override fun areContentsTheSame(old: Character, new: Character): Boolean {
                    return old == new
                }

            }
        }

    }
}