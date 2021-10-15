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
out vec3 toCameraVector;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;
uniform float useFakeLighting;

const float density = 0.008;
const float gradient = 2.0;

void main()
{
	vec4 worldPosition = transformationMatrix * vec4(pos, 1.0);
	vec4 positionRelativeToCamera = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCamera;
	outputTextureCoords = textureCoords;
	
	vec3 realNormal = normal;
	if(useFakeLighting > 0.5)
	{
		realNormal = vec3(0.0,1.0,0.0);
	}
	
	surfaceNormal = (transformationMatrix * vec4(realNormal, 0.0)).xyz;
	toLightVector = lightPosition - worldPosition.xyz;
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
	
	float distance = length(positionRelativeToCamera.xyz);
	visibility = exp(-pow((distance*density),gradient));
	visibility = clamp(visibility,0.0,1.0);
	
}