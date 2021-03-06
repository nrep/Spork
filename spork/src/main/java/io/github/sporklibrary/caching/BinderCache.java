package io.github.sporklibrary.caching;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.interfaces.ObjectBinder;
import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.reflection.AnnotatedFields;
import io.github.sporklibrary.reflection.AnnotatedMethod;
import io.github.sporklibrary.reflection.AnnotatedMethods;
import io.github.sporklibrary.reflection.AnnotatedType;
import io.github.sporklibrary.reflection.AnnotatedTypes;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Holds the annotation type cache for one class. This only holds the data for the specified class
 * which excludes the cache of its superclasses.
 */
public final class BinderCache {
    private final List<ObjectBinder> objectBinders = new ArrayList<>();
    private final Class<?> annotatedType;
    public BinderCache(Class<?> annotatedType) {
        this.annotatedType = annotatedType;
    }

    /**
     * Update the cache with this binder
     *
     * @param fieldBinder      the field binder to cache annotated fields for
     * @param <AnnotationType> the annotation to search for in the annotated type
     */
    public <AnnotationType extends Annotation> void register(FieldBinder<AnnotationType> fieldBinder) {
        Set<AnnotatedField<AnnotationType>> annotatedFields = AnnotatedFields.get(fieldBinder.getAnnotationClass(), annotatedType);

        if (!annotatedFields.isEmpty()) {
            objectBinders.add(new AnnotatedFieldBinder<>(fieldBinder, annotatedFields));
        }
    }

    /**
     * Update the cache with this binder
     *
     * @param methodBinder     the method binder to cache annotated methods for
     * @param <AnnotationType> the annotation to search for in the annotated type
     */
    public <AnnotationType extends Annotation> void register(MethodBinder<AnnotationType> methodBinder) {
        Set<AnnotatedMethod<AnnotationType>> annotatedMethods = AnnotatedMethods.get(methodBinder.getAnnotationClass(), annotatedType);

        if (!annotatedMethods.isEmpty()) {
            objectBinders.add(new AnnotatedMethodBinder<>(methodBinder, annotatedMethods));
        }
    }

    /**
     * Update the cache with this binder
     *
     * @param typeBinder       the type binder to cache annotated types for
     * @param <AnnotationType> the annotation to search for in the annotated type
     */
    public <AnnotationType extends Annotation> void register(TypeBinder<AnnotationType> typeBinder) {
        @Nullable AnnotatedType<AnnotationType> annotatedType = AnnotatedTypes.get(typeBinder.getAnnotationClass(), this.annotatedType);

        if (annotatedType != null) {
            objectBinders.add(new AnnotatedTypeBinder<>(typeBinder, annotatedType));
        }
    }

    /**
     * @return the list of all ObjectBinder instances managed for this cache.
     */
    public List<ObjectBinder> getBinders() {
        return objectBinders;
    }
}
