## cw-three
#####BBK SDP 2015/2016
### Ray Tracer

===
*authors:*

+ [Vladimirs Ivanov] [1]
+ [Annabel Jump] [2]
+ [Rustam Drake] [3]
+ [Federico Bartolomei] [4]

based on *Kwabena Aning*'s [sdp2016-ray-tracer][5]

[1]: https://github.com/BBK-PiJ-2015-87
[2]: https://github.com/annabeljump
[3]: https://
[4]: https://github.com/f-bartholomews

[5]: https://bitbucket.org/kaning/sdp2016-ray-tracer
===
######*Description:*
Ray tracing renderer uses akka actors model for distributed calculation.
Image can be divided into pre-defined height strips and rendered by multiple nodes.

All objects and lights are loaded from a text data file into a Scene object.

Coordinator actor is used to start rendering, collect statistics and set colour of a final image.

RenderingEngine actors encapsulate ray tracing functionality.

Settings class is used to configure renderer. Such parameters as output image width, height, antialiasing,
global ambient light, background colour and height of a render region can be updated in this class.

Counter class is used to calculate total amount of rays casted (which should equals to a product of image width
and height for AntiAliasing=1, as 1 ray is casted per 1 pixel). To avoid race condition whilst incrementing
counters by multiple threads (actors) AtomicIntegers are used as fields.

######*Development TO DO List:*

+ *create a Camera class holding camera functionality currently in Scene class* --DONE

+ *refactor Scene class to have just objects and lights* --DONE

+ *create a Counter class holding hits, rays and other counters* --DONE

+ *create Protocols defining Actors' Messages* --DONE
    
+ *create a Coordinator Actor* --DONE

+ *create a Worker Actor (e.g. RenderingEngine)* --DONE

+ *move the functionality of rendering a part (horizontal strip) of image into the worker actor* --DONE

+ *move all configuration of renderer to a Settings class (width, height, antiAliasing, ambient etc.)* --DONE

+ *update the Tracer main class accordingly* --DONE


######*Refactoring TO DO List:*

+ *refactor for Scene. Move file reading to a separate class.*

+ *refactor for Settings. Settings can be provided in a text file as well. Reader class can be used same as in Scene to load config.*

+ *refactor for Coordinator. Image division to regions can be done in a separate class or method (startOfSegments and endOfSegments)*


######*Testing TO DO List:*

+ *tests for provided classes: Vector, Sphere, Ray, Image *

+ *tests for Coordinator*

+ *tests for RenderingEngine*

+ *tests for Scene and new Reader class*

+ *tests for Tracer*


######*Development methodology used:*

For current project development we used pair programming as per XP methodology. That way each member
of a team share the equivalent knowledge of code base.