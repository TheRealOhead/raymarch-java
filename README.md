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
  - This would be RIDICULOUSLY fast, I really ought to do this
  - Perhaps using [JLSL](https://github.com/jglrxavpok/JLSL)
- [ ] Baking scenes
  - This would entail taking many, many sample points across the scene, then compressing it using a similar algorithm to JPEG. The resulting data can be saved as a custom data file.