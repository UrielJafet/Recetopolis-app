package com.example.recetopolis.core.network

import com.example.recetopolis.data.remote.dto.*
import kotlinx.coroutines.delay

class FakeApiService {

    // Usuarios hardcodeados
    private val users = mutableListOf(
        UserDto("1", "Juan Chef", "juan@recetopolis.com", "https://i.pravatar.cc/150?u=1"),
        UserDto("2", "Maria Cocina", "maria@recetopolis.com", "https://i.pravatar.cc/150?u=2")
    )

    private val passwords = mapOf(
        "juan@recetopolis.com" to "123456",
        "maria@recetopolis.com" to "123456"
    )

    // Recetas hardcodeadas
    private val recipes = mutableListOf(
        RecipeDto(
            id = "1",
            title = "Tacos al Pastor",
            description = "Tacos tradicionales mexicanos con carne de cerdo marinada",
            ingredients = listOf("1kg carne de cerdo", "3 chiles guajillo", "1 piña", "tortillas de maíz", "cebolla", "cilantro"),
            steps = listOf("Marinar la carne", "Asar en trompo", "Picar y servir en tortillas", "Agregar piña, cebolla y cilantro"),
            category = "Mexicana",
            difficulty = "Media",
            imageUrl = "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=800",
            authorId = users[0],
            ratings = listOf(
                RatingDto("2", 5, "¡Deliciosos!"),
                RatingDto("1", 4, "Muy buenos, le falta salsa")
            ),
            createdAt = "2024-01-15"
        ),
        RecipeDto(
            id = "2",
            title = "Pasta Carbonara",
            description = "Pasta italiana con huevo, queso y panceta",
            ingredients = listOf("400g spaghetti", "200g panceta", "4 huevos", "100g queso pecorino", "pimienta negra"),
            steps = listOf("Cocinar la pasta", "Freír la panceta", "Mezclar huevos y queso", "Combinar todo con la pasta caliente"),
            category = "Italiana",
            difficulty = "Fácil",
            imageUrl = "https://images.unsplash.com/photo-1612874742237-6526221588e3?w=800",
            authorId = users[1],
            ratings = listOf(
                RatingDto("1", 5, "Auténtica italiana")
            ),
            createdAt = "2024-02-20"
        ),
        RecipeDto(
            id = "3",
            title = "Sushi Roll",
            description = "Rollos de sushi con salmón y aguacate",
            ingredients = listOf("2 tazas arroz para sushi", "salmon fresco", "aguacate", "alga nori", "vinagre de arroz"),
            steps = listOf("Cocinar el arroz", "Extender alga nori", "Colocar arroz, salmón y aguacate", "Enrollar y cortar"),
            category = "Japonesa",
            difficulty = "Difícil",
            imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=800",
            authorId = users[0],
            ratings = emptyList(),
            createdAt = "2024-03-10"
        ),
        RecipeDto(
            id = "4",
            title = "Paella Valenciana",
            description = "Arroz con pollo, conejo y verduras",
            ingredients = listOf("500g arroz bomba", "pollo", "conejo", "judías verdes", "garrofón", "azafrán"),
            steps = listOf("Sofreír la carne", "Añadir verduras", "Incorporar el arroz", "Cocinar con caldo"),
            category = "Española",
            difficulty = "Difícil",
            imageUrl = "https://images.unsplash.com/photo-1534080564583-6be75777b70a?w=800",
            authorId = users[1],
            ratings = listOf(
                RatingDto("1", 5, "Como en Valencia")
            ),
            createdAt = "2024-04-05"
        )
    )

    // Favoritos por usuario
    private val favorites = mutableMapOf(
        "1" to mutableListOf("2", "4"),  // Juan favoritos
        "2" to mutableListOf("1")         // Maria favoritos
    )

    // Token simulado
    private var currentToken: String? = null
    private var currentUser: UserDto? = null

    // ========== AUTH ==========

    suspend fun login(request: LoginRequest): AuthResponse {
        delay(800) // Simula red

        val password = passwords[request.email]
        if (password == null || password != request.password) {
            throw Exception("Invalid email or password")
        }

        val user = users.find { it.email == request.email }!!
        val token = "fake_jwt_${user.id}_${System.currentTimeMillis()}"

        currentToken = token
        currentUser = user

        return AuthResponse(token, user)
    }

    suspend fun register(request: RegisterRequest): AuthResponse {
        delay(800)

        if (users.any { it.email == request.email }) {
            throw Exception("Email already registered")
        }

        val newUser = UserDto(
            id = (users.size + 1).toString(),
            username = request.username,
            email = request.email,
            avatar = "https://i.pravatar.cc/150?u=${users.size + 1}"
        )

        users.add(newUser)
        (passwords as MutableMap)[request.email] = request.password

        val token = "fake_jwt_${newUser.id}_${System.currentTimeMillis()}"
        currentToken = token
        currentUser = newUser

        return AuthResponse(token, newUser)
    }

    suspend fun getProfile(token: String): UserDto {
        delay(300)
        validateToken(token)
        return currentUser ?: throw Exception("No session")
    }

    // ========== RECIPES ==========

    suspend fun getRecipes(): List<RecipeDto> {
        delay(500)
        return recipes
    }

    suspend fun getRecipeById(id: String): RecipeDto {
        delay(300)
        return recipes.find { it.id == id } ?: throw Exception("Recipe not found")
    }

    suspend fun searchRecipes(query: String?, category: String?, difficulty: String?): List<RecipeDto> {
        delay(400)
        return recipes.filter { recipe ->
            val matchesQuery = query?.let {
                recipe.title.contains(it, ignoreCase = true) ||
                        recipe.description.contains(it, ignoreCase = true)
            } ?: true
            val matchesCategory = category?.let { recipe.category == it } ?: true
            val matchesDifficulty = difficulty?.let { recipe.difficulty == it } ?: true

            matchesQuery && matchesCategory && matchesDifficulty
        }
    }

    suspend fun createRecipe(token: String, recipe: CreateRecipeRequest): RecipeDto {
        delay(600)
        validateToken(token)

        val newRecipe = RecipeDto(
            id = (recipes.size + 1).toString(),
            title = recipe.title,
            description = recipe.description,
            ingredients = recipe.ingredients,
            steps = recipe.steps,
            category = recipe.category,
            difficulty = recipe.difficulty,
            imageUrl = recipe.imageUrl ?: "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=800",
            authorId = currentUser,
            ratings = emptyList(),
            createdAt = "2024-06-11"
        )

        recipes.add(newRecipe)
        return newRecipe
    }

    // ========== FAVORITES ==========

    suspend fun getFavorites(token: String): List<RecipeDto> {
        delay(400)
        validateToken(token)
        val userId = currentUser?.id ?: return emptyList()
        val favIds = favorites[userId] ?: return emptyList()
        return recipes.filter { it.id in favIds }
    }

    suspend fun addFavorite(token: String, recipeId: String) {
        delay(300)
        validateToken(token)
        val userId = currentUser?.id ?: throw Exception("No user")
        favorites.getOrPut(userId) { mutableListOf() }.add(recipeId)
    }

    suspend fun removeFavorite(token: String, recipeId: String) {
        delay(300)
        validateToken(token)
        val userId = currentUser?.id ?: throw Exception("No user")
        favorites[userId]?.remove(recipeId)
    }

    // ========== CATEGORIES ==========

    suspend fun getCategories(): List<CategoryDto> {
        delay(200)
        return listOf(
            CategoryDto("1", "Mexicana", "🌮"),
            CategoryDto("2", "Italiana", "🍝"),
            CategoryDto("3", "Japonesa", "🍣"),
            CategoryDto("4", "Española", "🥘"),
            CategoryDto("5", "Postres", "🍰")
        )
    }

    // ========== HELPERS ==========

    private fun validateToken(token: String) {
        if (!token.startsWith("fake_jwt_")) {
            throw Exception("Invalid token")
        }
        currentToken = token
    }
}