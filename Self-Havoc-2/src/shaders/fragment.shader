// FRAGMENT SHADER
// Processes each pixel of any object/shape that is to be rendered
// Takes input from the vertex shader
// Produces output in the form of RGB values (color)

#version 400 core

in vec3 col;

out vec4 pix_col;

void main()
{
	pix_col = vec4(col, 1.0);
}