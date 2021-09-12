// VERTEX SHADER
// Processes each vertex of any shape/object and produces the desired output
// Takes input from VAO (vertices co-ordinates)
// Passes output to fragment shader

#version 400 core

in vec3 pos;

out vec3 col;

void main()
{
	gl_Position = vec4(pos, 1.0);
	col = vec3(pos.x+0.4, 0.7, pos.z+0.6);
}