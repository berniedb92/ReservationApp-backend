package it.bernie.prenotazione.webservice;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import it.bernie.prenotazione.webservice.utility.UtilityCalcolo;
import it.bernie.prenotazione.webservice.utility.UtilityControllo;

@Configuration
public class MessageConfig 
{
	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator()
	{
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());

		return bean;
	}
	
	@Bean
	public LocaleResolver localeResolver()
	{
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		//sessionLocaleResolver.setDefaultLocale(LocaleContextHolder.getLocale());
		sessionLocaleResolver.setDefaultLocale(new Locale("it"));
			
		return sessionLocaleResolver;
	}

	@Bean
	public MessageSource messageSource()
	{
		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
		resource.setBasename("messages_it");
		resource.setUseCodeAsDefaultMessage(true);

		return resource;
	}
	
	@Bean
	public UtilityControllo utilityControllo()
	{
		UtilityControllo controllo = new UtilityControllo();

		return controllo;
	}
	
	@Bean
	public UtilityCalcolo utilityCalcolo()
	{
		UtilityCalcolo calcolo = new UtilityCalcolo();

		return calcolo;
	}
}

