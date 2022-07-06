package com.dspot.dspotandroid.data.dummy

import com.dspot.dspotandroid.R
import com.dspot.dspotandroid.data.model.DashboardItem

class DashboardItemsDataSource {
    companion object {
        fun getAllDashboardItems(): List<DashboardItem> {
            return listOf(
                DashboardItem(
                    0,
                    "50 users list",
                    "Get 50 users from https://randomuser.me and display them in a list",
                    R.drawable.ic_user_list_thin_svgrepo_com,
                    0,
                    background = R.drawable.card_purple
                ),
                DashboardItem(
                    1,
                    "Paginated list",
                    "Paginated list with page size 50, user detail view and location",
                    R.drawable.ic_list_svgrepo_com,
                    0,
                    background = R.drawable.card_red
                ),
            )
        }
    }
}