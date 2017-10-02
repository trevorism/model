package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.event.DefaultEventProducer
import com.trevorism.event.EventProducer
import com.trevorism.gcloud.model.Chore
import com.trevorism.gcloud.model.ChoreChart
import com.trevorism.http.util.CorrelationGenerator
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

/**
 * @author tbrooks
 */
@Api
@Path("/chore")
class ChoreController {

    private static final Logger log = Logger.getLogger(ChoreController.class.name)
    private final EventProducer<Chore> producer = new DefaultEventProducer<>()
    private final Repository<Chore> repository = new PingingDatastoreRepository<>(Chore)

    @ApiOperation(value = "Send a chore as an event")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Chore createChore(Chore chore){
        String id = CorrelationGenerator.generate()
        log.info("Creating chore with correlationId: ${id}")
        producer.sendCorrelatedEvent("chore", chore, id)
        return chore

    }

    @ApiOperation(value = "Build a chart")
    @GET
    @Path("chart")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ChoreChart getChartData(){
        List<Chore> chores = repository.list()

        long days = getDays(chores)
        def categoryToGoalMap = [exercise:3,meditation:2,household:2,business:4,administrative:7]

        ChoreChart choreChart = createChoreChart(categoryToGoalMap, chores, days)

        return choreChart

    }

    private ChoreChart createChoreChart(LinkedHashMap<String, Integer> categoryToGoalMap, chores, long days) {
        ChoreChart choreChart = new ChoreChart()
        categoryToGoalMap.each { k, v ->
            choreChart.countOfActivty << chores.count { it.type == k }
            choreChart.goalOfActivty << Math.round(days / v)
        }
        choreChart
    }

    private long getDays(List<Chore> chores) {
        Date minDate = (chores.min { it.date }).date
        Date maxDate = (chores.max { it.date }).date

        long days = TimeUnit.DAYS.convert(maxDate.getTime() - minDate.getTime(), TimeUnit.MILLISECONDS)
        days
    }

}
