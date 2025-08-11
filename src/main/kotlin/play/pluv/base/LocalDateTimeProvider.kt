package play.pluv.base

import org.springframework.data.auditing.DateTimeProvider
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAccessor
import java.util.*

@Component
class LocalDateTimeProvider : DateTimeProvider {
    override fun getNow(): Optional<TemporalAccessor> {
        return Optional.of(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
    }
}