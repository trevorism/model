package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.model.ChoreChart
import org.junit.Test

/**
 * @author tbrooks
 */
class ChoreControllerTest {
    @Test
    void testChoreData() {
        ChoreController controller = new ChoreController()
        ChoreChart chartData = controller.getChartData()

        println chartData.countOfActivty
        println chartData.goalOfActivty
    }
}
