package com.android.basicstructure.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.basicstructure.R
import com.android.basicstructure.core.ui.BaseFragment
import com.android.basicstructure.core.util.GridSpacingDecoration
import com.android.basicstructure.core.util.addReplaceFragment
import com.android.basicstructure.databinding.FragmentHomeBinding
import com.android.basicstructure.model.response.DashboardCategoriesData
import com.android.basicstructure.model.response.DashboardData
import com.android.basicstructure.view.adapter.DashboardAdapter
import com.android.basicstructure.view.adapter.DashboardCategoriesAdapter
import kotlinx.coroutines.launch

/**
 * Created by Jeetesh surana.
 */

@SuppressLint("NotifyDataSetChanged")
class DashboardFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private var mDashboardAdapter: DashboardAdapter? = null
    private var spanCount: Int = 2
    private var mDashboardCategoriesAdapter: DashboardCategoriesAdapter? = null

    private var mDashboardDataList = ArrayList<DashboardData>()
    private var mDashboardCategoriesDataList = ArrayList<DashboardCategoriesData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        lifecycleScope.launch {
            initCategoriesRecyclerView()
            initCategoriesList()
            initDashboardRecyclerView()
            initDashboardList()
        }
    }

    private fun initDashboardList() {
        mDashboardDataList.clear()
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardDataList.add(DashboardData())
        mDashboardAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initCategoriesList() {
        mDashboardCategoriesDataList.clear()
        mDashboardCategoriesDataList.add(DashboardCategoriesData(isSelected = true))
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesDataList.add(DashboardCategoriesData())
        mDashboardCategoriesAdapter?.notifyDataSetChanged()
    }

    private fun initCategoriesRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvCategories.layoutManager = layoutManager

        mDashboardCategoriesAdapter =
            DashboardCategoriesAdapter(
                requireContext(),
                mDashboardCategoriesDataList,
                object : DashboardCategoriesAdapter.ItemClickListener {
                    override fun itemClick(position: Int) {

                    }
                })
        binding.rvCategories.adapter = mDashboardCategoriesAdapter
    }

    private fun initDashboardRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.rvDashboard.layoutManager = layoutManager
        mDashboardAdapter =
            DashboardAdapter(mDashboardDataList, object : DashboardAdapter.ItemClickListener {
                override fun itemClick(position: Int) {
                    activity?.addReplaceFragment(
                        R.id.fl_container, DashboardDetailFragment(),
                        addFragment = true,
                        addToBackStack = true
                    )
                }
            })
        binding.rvDashboard.adapter = mDashboardAdapter

        val spacing = resources.getDimension(R.dimen.dp_24).toInt() // 50px
        val includeEdge = false
        binding.rvDashboard.addItemDecoration(
            GridSpacingDecoration(
                spanCount,
                spacing,
                includeEdge,
                0
            )
        )
    }
}