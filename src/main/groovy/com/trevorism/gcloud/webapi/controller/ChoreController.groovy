package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.PingingDatastoreRepository
import com.trevorism.data.Repository
import com.trevorism.gcloud.model.Chore
import com.trevorism.http.util.CorrelationGenerator

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.util.logging.Logger

/**
 * @author tbrooks
 */
@Path("/chore")
class ChoreController {

    private final Repository<Chore> repository = new PingingDatastoreRepository<>(Chore)
    private static final Logger log = Logger.getLogger(ChoreController.class.name)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Chore createChore(Chore chore){
        String id = CorrelationGenerator.generate()
        log.info("Creating chore with correlationId: ${id}")
        repository.create(chore, id)


    }
}
