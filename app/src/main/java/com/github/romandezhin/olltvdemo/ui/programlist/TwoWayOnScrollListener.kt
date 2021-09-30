package com.github.romandezhin.olltvdemo.ui.programlist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class TwoWayOnScrollListener(
    private val adapter: ProgramAdapter,
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        if (dy > STARTING_DIRECTION && lastPosition == adapter.itemCount - 1) {
            loadMore(adapter.getLastItemId(), FORWARD_DIRECTION)
        }

        val firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (dy < STARTING_DIRECTION && firstPosition == 0) {
            loadMore(adapter.getFirstItemId(), BACK_DIRECTION)
        }
    }

    abstract fun loadMore(borderId: Int, direction: Int)

    companion object {
        private const val FORWARD_DIRECTION = 1
        private const val BACK_DIRECTION = -1
        private const val STARTING_DIRECTION = 0
    }
}