package org.terasoluna.tourreservation;

import org.springframework.boot.SpringApplication;

public class TestTourreservationApplication {

	public static void main(String[] args) {
		SpringApplication.from(TourreservationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
