package com.alexkn.syntact.data.model.views

import androidx.room.DatabaseView

@DatabaseView("SELECT (SELECT sum(timesSolved) FROM solvableitem s WHERE s.disabled = 0) as solved, " +
        "(SELECT count(*) FROM solvableitem s where s.lastSolved > strftime('%s','now','start of day') AND s.disabled = 0) as solvedToday")
data class PlayerStats(
        var solved: Int,
        var solvedToday: Int
)