package io.github.sporklibrary.android.internal;

import android.app.Fragment;
import android.content.ContentProvider;
import android.content.Context;
import android.view.View;

import io.github.sporklibrary.android.interfaces.ContextProvider;
import io.github.sporklibrary.android.interfaces.ContextResolver;
import io.github.sporklibrary.android.interfaces.ViewProvider;
import io.github.sporklibrary.annotations.Nullable;

public class DefaultContextResolver implements ContextResolver {

	@Override
	public @Nullable Context resolveContext(Object object) {
		Class<?> object_class = object.getClass();

		if (View.class.isAssignableFrom(object_class)) {
			return ((View) object).getContext();
		} else if (Fragment.class.isAssignableFrom(object_class)) {
			return ((Fragment) object).getActivity();
		} else if (Context.class.isAssignableFrom(object_class)) {
			return (Context) object;
		} else if (ContentProvider.class.isAssignableFrom(object_class)) {
			return ((ContentProvider) object).getContext();
		} else if (ContextProvider.class.isAssignableFrom(object_class)) {
			return ((ContextProvider) object).getContext();
		} else if (ViewProvider.class.isAssignableFrom(object_class)) {
			return ((ViewProvider) object).getView().getContext();
		} else {
			return null;
		}
	}
}