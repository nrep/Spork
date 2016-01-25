package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.binders.AnnotatedField;
import io.github.sporklibrary.binders.AnnotatedFields;
import io.github.sporklibrary.binders.FieldBinder;

/**
 * The default FieldBinder that binds field annotated with the Bind annotation.
 */
public class ComponentFieldBinder implements FieldBinder<BindComponent>
{
	private final ComponentInstanceManager mComponentInstanceManager = new ComponentInstanceManager();

	@Override
	public Class<BindComponent> getAnnotationClass()
	{
		return BindComponent.class;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindComponent> annotatedField)
	{
		Object instance = mComponentInstanceManager.getInstance(annotatedField, object);

		AnnotatedFields.set(annotatedField, object, instance);
	}
}
