// VERTEX SHADER
// Processes each vertex of any shape/object and produces the desired output
// Takes input from VAO (vertices co-ordinates)
// Passes output to fragment shader

#version 400 core

in vec3 pos;
in vec2 textureCoords;

out vec2 outputTextureCoords;

void main()
{
	gl_Position = vec4(pos, 1.0);
	outputTextureCoords = textureCoords;
}