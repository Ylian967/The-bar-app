<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import type { Ingredient } from '../../types/models'

const ingredients = ref<Ingredient[]>([])
const nouveau = ref('')

async function charger() {
  ingredients.value = await catalogueService.listerIngredients()
}
async function ajouter() {
  if (!nouveau.value.trim()) return
  await catalogueService.creerIngredient({ nom: nouveau.value.trim() })
  nouveau.value = ''
  charger()
}
async function supprimer(i: Ingredient) {
  if (!confirm(`Supprimer l'ingrédient « ${i.nom} » ?`)) return
  await catalogueService.supprimerIngredient(i.id)
  charger()
}

onMounted(charger)
</script>

<template>
  <div>
    <div class="ajout">
      <input v-model="nouveau" placeholder="Nouvel ingrédient…" @keyup.enter="ajouter" />
      <button class="add" @click="ajouter">+ Ajouter</button>
    </div>
    <ul class="liste">
      <li v-for="i in ingredients" :key="i.id">
        <span>{{ i.nom }}</span>
        <button class="del" @click="supprimer(i)" title="Supprimer">🗑️</button>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.ajout { display: flex; gap: 10px; margin-bottom: 18px; }
.ajout input { flex: 1; background: #fff; border: 1.5px solid var(--line); border-radius: 14px; padding: 12px 14px; font-family: inherit; font-size: 14px; }
.add { background: linear-gradient(135deg, var(--coral), var(--mango)); color: #fff; font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 13px; padding: 0 18px; border-radius: 14px; }
.liste { list-style: none; display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 10px; }
.liste li { display: flex; justify-content: space-between; align-items: center; background: #fff; border-radius: 16px; padding: 12px 16px; box-shadow: var(--shadow-sm); font-weight: 600; font-size: 14px; }
.del { background: #ffe4ea; width: 32px; height: 32px; border-radius: 10px; }
</style>
