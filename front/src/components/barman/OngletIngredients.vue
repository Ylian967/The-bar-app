<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import { imageUrl } from '../../services/http'
import type { Ingredient } from '../../types/models'

const ingredients = ref<Ingredient[]>([])
const nouveau = ref('')
const recherche = ref('')

const listeFiltree = computed(() => {
  const q = recherche.value.trim().toLowerCase()
  return ingredients.value.filter((i) => i.nom.toLowerCase().includes(q))
})

async function charger() {
  ingredients.value = await catalogueService.listerIngredients()
}
async function ajouter() {
  if (!nouveau.value.trim()) return
  await catalogueService.creerIngredient({ nom: nouveau.value.trim() })
  nouveau.value = ''
  charger()
}
async function basculer(i: Ingredient) {
  await catalogueService.basculerDisponible(i.id)
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
    <p class="aide">Décochez un ingrédient en rupture : les cocktails qui l'utilisent ne seront plus proposés.</p>
    <div class="entete">
      <input v-model="nouveau" placeholder="Nouvel ingrédient…" @keyup.enter="ajouter" />
      <button class="add" @click="ajouter">+ Ajouter</button>
    </div>

    <div class="recherche">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round">
        <circle cx="11" cy="11" r="7" /><path d="M20 20l-3.5-3.5" />
      </svg>
      <input v-model="recherche" type="text" placeholder="Rechercher un ingrédient…" />
    </div>

    <p v-if="listeFiltree.length === 0" class="etat">Aucun ingrédient trouvé.</p>
    <div v-else class="grid">
      <div v-for="i in listeFiltree" :key="i.id" class="card" :class="{ rupture: !i.disponible }">
        <img v-if="i.imageUrl" :src="imageUrl(i.imageUrl)" :alt="i.nom" />
        <div v-else class="ph">{{ i.nom.charAt(0) }}</div>
        <div class="nom">{{ i.nom }}</div>
        <button class="stock" :class="{ on: i.disponible }" @click="basculer(i)">
          {{ i.disponible ? 'En stock' : 'Rupture' }}
        </button>
        <button class="del" title="Supprimer" @click="supprimer(i)">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M4 7h16M9 7V5h6v2M6 7l1 13h10l1-13" /></svg>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.aide { font-size: 13px; color: var(--ink-soft); margin-bottom: 14px; }
.entete { display: flex; gap: 10px; margin-bottom: 18px; max-width: 480px; }
.entete input { flex: 1; background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); border: 1.5px solid var(--line); border-radius: 14px; padding: 12px 14px; font-family: inherit; font-size: 14px; }
.add { background: var(--btn); border: 1px solid var(--btn-stroke); backdrop-filter: blur(16px) saturate(150%); -webkit-backdrop-filter: blur(16px) saturate(150%); color: #fff; font-family: 'Sora', sans-serif; font-weight: 700; font-size: 13px; padding: 0 18px; border-radius: 14px; flex-shrink: 0; box-shadow: var(--btn-sheen); }

.recherche { display: flex; align-items: center; gap: 10px; max-width: 480px; margin-bottom: 18px; background: var(--glass-strong); border: 1.5px solid var(--line); border-radius: 14px; padding: 11px 14px; backdrop-filter: blur(20px) saturate(150%); }
.recherche svg { width: 17px; height: 17px; color: var(--ink-soft); flex-shrink: 0; }
.recherche input { flex: 1; min-width: 0; border: none; outline: none; background: transparent; font-family: inherit; font-size: 14px; }

.grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(150px, 1fr)); gap: 14px; }
.card { background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); border-radius: 18px; box-shadow: var(--shadow-sm); padding: 14px; display: flex; flex-direction: column; align-items: center; gap: 8px; position: relative; }
.card.rupture { opacity: 0.6; }
.card img, .card .ph { width: 56px; height: 56px; border-radius: 14px; object-fit: cover; }
.card .ph { background: var(--cream); display: grid; place-items: center; font-family: 'Sora', sans-serif; font-weight: 700; color: var(--ink-soft); font-size: 22px; }
.card .nom { font-family: 'Sora', sans-serif; font-weight: 600; font-size: 13px; text-align: center; }
.stock { width: 100%; border-radius: 999px; padding: 7px; font-weight: 700; font-size: 12px; background: rgba(255,77,141,0.18); color: var(--coral-d); }
.stock.on { background: rgba(63,224,176,0.16); color: var(--teal-d); }
.del { position: absolute; top: 8px; right: 8px; width: 28px; height: 28px; border-radius: 9px; background: var(--cream); color: var(--coral-d); display: grid; place-items: center; }
.del svg { width: 14px; height: 14px; }
</style>
