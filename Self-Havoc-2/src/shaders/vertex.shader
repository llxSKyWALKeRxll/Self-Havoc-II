// VERTEX SHADER
// Processes each vertex of any shape/object and produces the desired output
// Takes input from VAO (vertices co-ordinates)
// Passes output to fragment shader

#version 400 core

in vec3 pos;
in vec2 textureCoords;

out vec3 colour;
out vec2 outputTextureCoords;

uniform mat4 transformationMatrix;

void main()
{
	gl_Position = transformationMatrix * vec4(pos, 1.0);
	outputTextureCoords = textureCoords;
	colour = vec3(pos.x+0.5, 0.0, pos.y+0.5);
}