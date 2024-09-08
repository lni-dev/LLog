# LLog
LLog is a logging library used by my other projects.

## Setting up logging
First a java resource file named `llog.properties` in the folder llog is required. This file contains properties about the logger implemantion, which should be used for logging:
```properties
logger=de.linusdev.llog.impl.streamtext.StreamTextLogger
logTo=System.out
autoFlush=true
minLogLevel=-100
```
The property `logger` points to the logger implementation class to use. Different implemantions may log to different medias.

## Common Logging properties
As you can see above, there are different properties, which can be set. Not every `Logger` implementation supports every property. Each implementation states which properties are supported and may also specify custom properties.
- `logTo`: This property specifies, if possible, where the `Logger` should output its log to. The values of this property may vary depending on the implementation used. Example values are `System.out` or a file path like `{self.location.parent}/log.txt`.
- `autoFlush`: Some logger implementations may require to be flushed similar to jave output streams. If this property is set to `true`, those will automatically flush itself after every log operation. This may be required if it is possible for your application to fail ungracefully without flushing the `Logger` causing some log to be lost.
- `minLogLevel`: May be set to the minimum log level, a log message must have to actually be logged. All log messages, whose log level is less than `minLogLevel` will be ignored. The value may be an integer or the name of a standard log level. See log level for more information. Default value is `Debug`.

## Property Expansion (ReplaceObjects)
The values of properties are expanded before the logger is created. These expansions have the following syntax: {obj.obj}, {obj.value}, {obj.obj.value}, etc. Logger implementations may add their own expansion objects. The example `{self.location.parent}` expands to the directory in which the jar file of the currently running application is located.

### Current Objects
Current objects that can always be expanded to.
Objects and sub-objects are declared like this:<br>
Type `name`: description.
#### Object self`
expands to none.<br>
Sub-objects:
- LocationObject `location`: Jar path

### Current Object Types
Current Types of objects. These are used in other objects. Sub-objects are declared like this:<br>
Type `name`: description.

#### LocationObject
Expands to the path (file/dir) it represents with no trailing "/".<br>
Sub-objects:
- LocationObject `parent`: parent directory

## Logger Implementations

### StreamTextLogger
This implementation logs to a simple output stream.
supported properties are: `logTo`, `autoFlush`, `minLogLevel` and `useAnsiColors`.

#### `logTo`
Specifies where to log to. May be `System.out` or a path to a file.

#### `useAnsiColors`
If set to `true` the Logger will add ANSI color codes to its output, making the log easier to read. Note: Not every console supports these.

### MultiLogger
This implementation allows to log to multiple `Logger` instances. For example logging to two StreamTextLogger. One logging to `System.out` and the other one to a log file. The only possible property is `sub-logger-x`. `x` must be replaced with the number of the next logger starting at `1`. The value of this property points to another properties file, which contains the properties for this sub-logger. Example:
```properties
sub-logger-1=llog/multi/sysout.properties
sub-logger-2=llog/multi/file.properties
```
sysout.properties:
```properties
logger=de.linusdev.llog.impl.streamtext.StreamTextLogger
logTo=System.out
autoFlush=true
useAnsiColors=true
```
file.properties:
```properties
logger=de.linusdev.llog.impl.streamtext.StreamTextLogger
logTo=testOutput/multi/log.log
```

## Log Level
Each log level has a name and an integer value (its level). If the value is smaller than the current minimum log level, its messages will be discarded. The default minimum log level is `Debug`.

### Standard Log Level

|     Name     | Level |   Color    |
|:------------:|:-----:|:----------:|
|    Error     |  100  |    Red     |
|   Warning    |  70   |   Orange   |
|     Info     |  20   |    Cyan    |
|    Debug     |   0   |  Default   |
|  Debug Low   |  -5   |  Default   |
|     Data     |  -10  | Light Grey |
| Private Data | -100  | Light Grey |

## Todo List
- Localhost logger: Logger that logs to a website hosted on localhost.
- StringReplacer: In llog.properties file add string replacer for current time/date, to allow changing file names
  of the log file.
- StreamingTextLogger: Allow logging to custom output streams (currently on System.out is supported). This would also be great for writing better/automatic tests.
- Tests: Better/automatic/more tests.
