// FRAGMENT SHADER
// Processes each pixel of any object/shape that is to be rendered
// Takes input from the vertex shader
// Produces output in the form of RGB values (color)

#version 400 core

in vec2 outputTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 pix_col;

uniform sampler2D textureSampler;
uniform vec3 lightColour;

void main()
{
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVector = normalize(toLightVector);
	
	float dotProduct1 = dot(unitNormal, unitVector);
	float brightnessIntensity = max(0.0, dotProduct1);
	vec3 diffuse = brightnessIntensity * lightColour;
	
	pix_col = vec4(diffuse, 1.0) * texture(textureSampler, outputTextureCoords);
}