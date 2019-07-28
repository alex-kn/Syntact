package com.alexkn.syntact.data.model.views

import androidx.room.DatabaseView

@DatabaseView("SELECT (SELECT sum(timesSolved) FROM solvableitem) as solved, (SELECT count(*) FROM solvableitem s where s.lastSolved > strftime('%s','now','start of day')) as solvedToday")
data class PlayerStats(
        var solved: Int,
        var solvedToday: Int
)