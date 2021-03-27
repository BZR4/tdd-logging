package info.esdras.logging.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/logging")
@RestController
public class LoggingResource {

    private final Logger logger = LoggerFactory.getLogger(LoggingResource.class);

    @GetMapping
    public String index() {
        logger.trace("Uma mensagme TRACE.");
        logger.debug("Uma mensagme DEBUG.");
        logger.info("Uma mensagme INFO.");
        logger.warn("Uma mensagme WARN.");
        logger.error("Uma mensagme ERROR.");
        return "Cheque o LOG no console da aplicação!";
    }
}
