# Raymarch

Using [signed distance functions](https://en.wikipedia.org/wiki/Signed_distance_function), a camera casts rays into a scene.
These rays can use the minimum of all the [Thing](src/main/things/Thing.java)'s SDFs to guarantee how far it can travel before hitting something. 

## Implemented Features
- [x] Predefined objects
  - [x] Plane
  - [x] Sphere
- [x] SmoothMin
- [x] Colored directional and point [light sources](src/main/optics)
- [x] [Programmatic materials](src/main/materials)
  - [x] Perlin noise
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
- [ ] Transpile Java into GLSL so Rays can be cast using the GPU
    - Perhaps using [JLSL](https://github.com/jglrxavpok/JLSL)

## To-do
- [ ] Allow multiple directional lights per scene
- [ ] Implement offsetting [radial textures](src/main/materials/RadialTexture.java)
- [X] Make [MaterialData](src/main/materials/MaterialData.java) immutable
- [ ] Make [SmoothMinGroup](src/main/things/compoundThings/SmoothMinGroup.java)s lerp every aspect of [MaterialData](src/main/materials/MaterialData.java)
- [ ] Write my own implementation of Perlin noise and stop using code from 2002
- [ ] Make the effect of normal modifier scale with how perpendicular a ray is. As of now, near parallel rays reflecting off the surface of water are actually being modified to reflect back into the surface, appearing as black splotches on the surface of the water
- [ ] ~~Make [Vector](src/main/math/vectors/Vector3.java)s (2 & 3) inherit from a generic Vector class~~ I can't figure out how to this. It'd probably be less efficient, anyway
- [ ] Make [Things](src/main/things/Thing.java) an interface