# Raymarch

Using [signed distance functions](https://en.wikipedia.org/wiki/Signed_distance_function), a camera casts rays into a
scene. These rays can use the minimum of all the [Thing](src/main/things/Thing.java)'s SDFs to guarantee how far it can
travel before hitting something. 

## Implemented Features
- [x] Predefined objects
  - [x] Plane
  - [x] Sphere
- [x]
- [x] Directional and point [light sources](src/main/optics)
- [x] [Programmatic materials](src/main/materials)
- [x] Animated GIF Support
    - Using [animated-gif-lib](pom.xml)

## Things I'd like to add
- Things
  - [ ] [Triangles](src/main/things/Triangle.java)
    - I'm having trouble implementing the SDF, I may need to use a different reference
  - [ ] Cuboid
  - [ ] Cylinder
- [ ] Either Lua support or a custom text-defined format for scenes
- [ ] Video output
  - Need to figure out [Jaffree](pom.xml)
- [ ] Transpile Java into GLSL
  - Perhaps using [JLSL](https://github.com/jglrxavpok/JLSL)

## To-do
 - [ ] Make [SmoothMinGroup](src/main/things/compoundThings/SmoothMinGroup.java)s lerp every aspect of [MaterialData](src/main/materials/MaterialData.java)
 - [ ] Make [MaterialData](src/main/materials/MaterialData.java) immutable
 - [ ] Make [Vector](src/main/math/vectors/Vector3.java)s (2 & 3) inherit from a generic Vector class
 - [ ] Make the effect of normal modifier scale with how perpendicular a ray is. As of now, near parallel rays
       reflecting off the surface of water are actually being modified to reflect back into the surface, appearing as
       black splotches on the surface of the water.