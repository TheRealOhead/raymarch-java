# To-do

- [X] Make [MaterialData](src/main/materials/MaterialData.java) immutable
- [X] Make the effect of normal modifier scale with how perpendicular a ray is. ~~As of now, near parallel rays reflecting off the surface of water are actually being modified to reflect back into the surface, appearing as black splotches on the surface of the water~~ The black splotches are reduced, but still show up...
- [X] Make FragmentData immutable
- [X] Implement diffuse scattering
- [X] Implement offsetting [radial textures](src/main/materials/RadialTexture.java)
- [X] ~~Write my own implementation~~ of Perlin noise and stop using code from 2002
    - I ended up using a library for 4D noise
- [X] Allow multiple directional lights per scene
- [ ] Make things rotatable
- [ ] Make things able to be joined and xor'd and all that
- [ ] Make scene building more consistent
  - Good place to start is making a `build()` method instead of doing the building directly in the constructor
- [ ] Make [SmoothMinGroup](src/main/things/compoundThings/SmoothMinGroup.java)s lerp every aspect of [MaterialData](src/main/materials/MaterialData.java)
- [ ] Make [Things](src/main/things/Thing.java) an interface
- [ ] ~~Make [Vector](src/main/math/vectors/Vector3.java)s (2 & 3) inherit from a generic Vector class~~ I can't figure out how to this. It'd probably be less efficient, anyway
