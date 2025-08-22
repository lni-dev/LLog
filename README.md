# LLog
LLog is a logging library used by my other projects.

## Setting up logging
First a java resource file named `llog.properties` in the folder llog is required. This file contains properties about the logger implemantion, which should be used for logging:
```properties
logger=de.linusdev.llog.impl.streamtext.StreamTextLogger
logTo=OutputStream:java.lang.System.out
autoFlush=true
minLogLevel=-100
```
The property `logger` points to the logger implementation class to use. Different implemantions may log to different medias.

## Common Logging properties
As you can see above, there are different properties, which can be set. Not every `Logger` implementation supports every property. Each implementation states which properties are supported and may also specify custom properties.
- `logTo`: This property specifies, if possible, where the `Logger` should output its log to. The values of this property may vary depending on the implementation used. Example values are `OutputStream:java.lang.System.out` or a file path like `{self.location.parent}/log.txt`.
- `autoFlush`: Some logger implementations may require to be flushed similar to jave output streams. If this property is set to `true`, those will automatically flush itself after every log operation. This may be required if it is possible for your application to fail ungracefully without flushing the `Logger` causing some log to be lost.
- `minLogLevel`: May be set to the minimum log level, a log message must have to actually be logged. All log messages, whose log level is less than `minLogLevel` will be ignored. The value may be an integer or the name of a standard log level. See log level for more information. Default value is `Debug`.

## LLog properties
These properties are handled by LLog instead of the actual logger implementation (These are supported with any configuration):
- `redirectJavaUtilLoggingToLLog`: If set to `true`, the java.util.logging root logger will have all handlers removed and a new handler added, which 
  logs to the active llog-logger
- `noInit`: LLog will not initialize any logger. Used for testing purposes. Allows to enable a logger with properties in code using `LLog.init(props)`

## Property Expansion (ReplaceObjects)
The values of properties are expanded before the logger is created. These expansions have the following syntax: {obj.obj}, {obj.value}, {obj.obj.value}, etc. Logger implementations may add their own expansion objects. The example `{self.location.parent}` expands to the directory in which the jar file of the currently running application is located.

### Current Objects
Current objects that can always be expanded to.
Objects and sub-objects are declared like this:<br>
Type `name`: description.
- MutableReplaceObject `self`: The program itself.<br>
  Sub-objects:
  - LocationObject `location`: Jar path
- DatetimeObject `datetime`: current date and time.

### Current Object Types
Current Types of objects. These are used in other objects. Sub-objects are declared like this:<br>
Type `name`: description.

#### MutableReplaceObject
Expands to `obj`. Can have varying sub-objects.

#### LocationObject
Expands to the path (file/dir) it represents with no trailing "/".<br>
Sub-objects:
- LocationObject `parent`: parent directory

#### DatetimeObject
Expands to datetime in the following format: `yyyy-MM-dd_HH-mm-ss`.<br>
Sub-objects:
- String `day`: day in month
- String `month`: month in year
- String `year`: year
- String `minute`: minute of hour
- String `hour`: hour of day
- String `second`: second of minute
- String format: Will return datetime in given format. For example: `dd_MM_yy`.

## Logger Implementations

### StreamTextLogger
This implementation logs to a simple output stream.
supported properties are: `logTo`, `autoFlush`, `minLogLevel` and `useAnsiColors`.

#### `logTo`
Specifies where to log to. May point to a java OutputStream variable or a file.
- File syntax: `logTo=path/to/file`, for example `logTo=logs/log.txt`.
- OutputStream syntax: `logTo=OutputStream:some.package.ClassName.variable`, for example: `logTo=OutputStream:java.lang.System.out`.

#### `useAnsiColors`
If set to `true` the Logger will add ANSI color codes to its output, making the log easier to read. Note: Not every console supports these.

### MultiLogger
This implementation allows to log to multiple `Logger` instances. For example logging to two StreamTextLogger. One logging to `System.out` and the other one to a log file. The only possible property is `sub-logger-x`. `x` must be replaced with the number of the next logger starting at `1`. The value of this property points to another properties file, which contains the properties for this sub-logger. Example:
```properties
logger=de.linusdev.llog.impl.multi.MultiLoggerImpl
sub-logger-1=llog/multi/sysout.properties
sub-logger-2=llog/multi/file.properties
```
sysout.properties:
```properties
logger=de.linusdev.llog.impl.streamtext.StreamTextLogger
logTo=OutputStream:java.lang.System.out
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
- Tests: Better/automatic/more tests.
- Custom log format
