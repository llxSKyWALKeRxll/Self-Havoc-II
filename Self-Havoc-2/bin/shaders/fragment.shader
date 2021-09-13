// FRAGMENT SHADER
// Processes each pixel of any object/shape that is to be rendered
// Takes input from the vertex shader
// Produces output in the form of RGB values (color)

#version 400 core

in vec2 outputTextureCoords;

out vec4 pix_col;

uniform sampler2D textureSampler;

void main()
{
	pix_col = texture(textureSampler, outputTextureCoords);
}