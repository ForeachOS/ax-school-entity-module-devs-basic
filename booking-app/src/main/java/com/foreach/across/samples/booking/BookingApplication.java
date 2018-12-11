package com.foreach.across.samples.booking;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.modules.adminweb.AdminWebModule;
import com.foreach.across.modules.bootstrapui.BootstrapUiModule;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.web.AcrossWebModule;
import org.springframework.boot.SpringApplication;

@AcrossApplication(
		modules = {
				AcrossWebModule.NAME,
				AcrossHibernateJpaModule.NAME,
				EntityModule.NAME,
				BootstrapUiModule.NAME,
				AdminWebModule.NAME
		}
)
public class BookingApplication
{
	public static void main( String[] args ) {
		SpringApplication springApplication = new SpringApplication( BookingApplication.class );
		springApplication.run( args );
	}
}