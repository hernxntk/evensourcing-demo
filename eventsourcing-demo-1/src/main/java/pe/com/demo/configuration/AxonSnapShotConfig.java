package pe.com.demo.configuration;

import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonSnapShotConfig {
	
	@Bean
	public Snapshotter snapshotter(ApplicationContext ctx) {
		SpringAggregateSnapshotter snapshotter = SpringAggregateSnapshotter
				.builder()
				.eventStore(ctx.getBean(EventStore.class))
				.build();
		snapshotter.setApplicationContext(ctx);
		return snapshotter;
	}
	
	@Bean("clasicSnapShotter")
	public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, 5); 
	}
}
