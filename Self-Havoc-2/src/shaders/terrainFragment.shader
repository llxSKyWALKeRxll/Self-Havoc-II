// FRAGMENT SHADER
// Processes each pixel of any object/shape that is to be rendered
// Takes input from the vertex shader
// Produces output in the form of RGB values (color)

#version 400 core

in vec2 outputTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 pix_col;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main(void)
{

	vec4 blendMapColour = texture(blendMap,outputTextureCoords);
	float backTextureQuantity = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
	vec2 tiledCoords = outputTextureCoords * 40;
	vec4 backgroundTextureColour = texture(backgroundTexture,tiledCoords) * backTextureQuantity;
	vec4 rTextureColour = texture(rTexture,tiledCoords) * blendMapColour.r;
	vec4 gTextureColour = texture(gTexture,tiledCoords) * blendMapColour.g;
	vec4 bTextureColour = texture(bTexture,tiledCoords) * blendMapColour.b;
	
	vec4 finalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float dotProduct1 = dot(unitNormal, unitLightVector);
	float brightnessIntensity = max(0.2, dotProduct1);
	vec3 diffuse = brightnessIntensity * lightColour;
	
	vec3 unitCameraVector = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	
	float specularFactor = max(0.0, dot(reflectedLightDirection, unitCameraVector));
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * lightColour * reflectivity;
	
	pix_col = vec4(diffuse, 1.0) * finalColour + vec4(finalSpecular, 1.0);
	pix_col = mix(vec4(skyColour,1.0),pix_col,visibility);
}