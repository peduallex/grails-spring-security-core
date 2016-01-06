/* Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.springsecurity

import groovy.transform.CompileStatic
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry

/**
 * Unregisters auto-config beans registered by Boot.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
@CompileStatic
class SpringSecurityBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	protected static final String AUTOCONFIG_NAME = 'org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration'
	protected static final String SECURITY_PROPERTIES_NAME = 'securityProperties'

	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (beanFactory instanceof BeanDefinitionRegistry) {
			if (beanFactory.containsBeanDefinition(AUTOCONFIG_NAME)) {
				beanFactory.removeBeanDefinition AUTOCONFIG_NAME
			}

			if (beanFactory.containsBeanDefinition(SECURITY_PROPERTIES_NAME)) {
				if (beanFactory.getBeanDefinition(SECURITY_PROPERTIES_NAME).factoryBeanName == AUTOCONFIG_NAME) {
					beanFactory.removeBeanDefinition SECURITY_PROPERTIES_NAME
				}
			}
		}
	}
}