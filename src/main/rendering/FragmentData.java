package main.rendering;

import java.awt.*;

public record FragmentData(
    Color color,
    Color albedo,
    Color depth,
    Color normal,
    Color thread,
    Color complexity,
    Color stepCount,
    Color normalModifications
) {}