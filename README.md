# LLog
LLog is a logging library used by my other projects.

## Setting up logging
First a java resource file named `llog.properties` in the folder llog is required. This file contains properties about the logger implemantion, which should be used for logging:
```properties
logger=de.linusdev.llog.impl.streamtext.StreamTextLogger
logTo=System.out
autoFlush=true
useAnsiColors=true
minLogLevel=-100
```
The property `logger` points to the `Logger` implementation class to use. Different implemantions may log to different medias.

## Common Logging properties
As you can see above, there are different properties, which can be set. Not every `Logger` implementation supports every property. Each implementation states which properties are supported and may also specify custom properties.
- `logTo`: This property specifies, if possible, where the `Logger` should output its log to. The values of this property may vary depending on the implementation used. Example values are `System.out` or a file path like `{self.location.parent}/log.txt`
- `autoFlush`: 