package com.trevorism.gcloud.webapi.controller

import com.trevorism.event.DefaultEventProducer
import com.trevorism.event.EventProducer
import com.trevorism.gcloud.model.Chore
import com.trevorism.http.util.CorrelationGenerator
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.util.logging.Logger

/**
 * @author tbrooks
 */
@Api
@Path("/chore")
class ChoreController {

    private static final Logger log = Logger.getLogger(ChoreController.class.name)
    private final EventProducer<Chore> producer = new DefaultEventProducer<>()

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
}
