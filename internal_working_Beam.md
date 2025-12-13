# Internal Working of beam ?

## In a DoFn class, we don’t override any interface method,yet when we annotate a method with @ProcessElement,Beam magically treats it as the main processing method ?

 - 👉 Apache Beam uses reflection + annotations to discover lifecycle methods at runtime.
 - @ProcessElement is not magic — it’s metadata.
