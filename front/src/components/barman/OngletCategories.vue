<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import type { Categorie } from '../../types/models'

const categories = ref<Categorie[]>([])
const nouveau = ref('')

async function charger() {
  categories.value = await catalogueService.listerCategories()
}
async function ajouter() {
  if (!nouveau.value.trim()) return
  await catalogueService.creerCategorie({ nom: nouveau.value.trim() })
  nouveau.value = ''
  charger()
}
async function supprimer(c: Categorie) {
  if (!confirm(`Supprimer la catégorie « ${c.nom} » ?`)) return
  await catalogueService.supprimerCategorie(c.id)
  charger()
}

onMounted(charger)
</script>

<template>
  <div>
    <div class="ajout">
      <input v-model="nouveau" placeholder="Nouvelle catégorie…" @keyup.enter="ajouter" />
      <button class="add" @click="ajouter">+ Ajouter</button>
    </div>
    <ul class="liste">
      <li v-for="c in categories" :key="c.id">
        <span>{{ c.nom }}</span>
        <button class="del" @click="supprimer(c)" title="Supprimer">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M4 7h16M9 7V5h6v2M6 7l1 13h10l1-13" /></svg>
        </button>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.ajout { display: flex; gap: 10px; margin-bottom: 18px; }
.ajout input { flex: 1; background: #fff; border: 1.5px solid var(--line); border-radius: 14px; padding: 12px 14px; font-family: inherit; font-size: 14px; }
.add { background: linear-gradient(135deg, var(--coral), var(--mango)); color: #fff; font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 13px; padding: 0 18px; border-radius: 14px; }
.liste { list-style: none; display: flex; flex-direction: column; gap: 10px; max-width: 520px; }
.liste li { display: flex; justify-content: space-between; align-items: center; background: #fff; border-radius: 16px; padding: 14px 18px; box-shadow: var(--shadow-sm); font-family: 'Poppins', sans-serif; font-weight: 600; font-size: 15px; }
.del { background: #ffe4ea; color: var(--coral-d); width: 34px; height: 34px; border-radius: 10px; display: grid; place-items: center; }
.del svg { width: 16px; height: 16px; }
</style>
