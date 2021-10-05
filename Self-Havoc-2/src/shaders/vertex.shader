// VERTEX SHADER
// Processes each vertex of any shape/object and produces the desired output
// Takes input from VAO (vertices co-ordinates)
// Passes output to fragment shader

#version 400 core

in vec3 pos;
in vec2 textureCoords;
in vec3 normal;

out vec2 outputTextureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

void main()
{
	vec4 worldPosition = transformationMatrix * vec4(pos, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPosition;
	outputTextureCoords = textureCoords;
	
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	toLightVector = lightPosition - worldPosition.xyz; 
}