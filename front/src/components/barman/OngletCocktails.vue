<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import { imageUrl } from '../../services/http'
import type { Cocktail } from '../../types/models'
import FormulaireCocktail from './FormulaireCocktail.vue'
import ImportCocktail from './ImportCocktail.vue'

type Mode = 'aucun' | 'choix' | 'creer' | 'editer' | 'import'

const cocktails = ref<Cocktail[]>([])
const mode = ref<Mode>('aucun')
const cocktailEdite = ref<Cocktail | null>(null)

async function charger() {
  cocktails.value = await catalogueService.listerCocktails()
}
function ajouter() {
  cocktailEdite.value = null
  mode.value = 'choix'
}
function editer(c: Cocktail) {
  cocktailEdite.value = c
  mode.value = 'editer'
}
async function supprimer(c: Cocktail) {
  if (!confirm(`Supprimer « ${c.nom} » ?`)) return
  await catalogueService.supprimerCocktail(c.id)
  charger()
}
function termine() {
  mode.value = 'aucun'
  charger()
}

onMounted(charger)
</script>

<template>
  <div class="wrap">
    <div class="liste">
      <div class="head">
        <span class="compte">{{ cocktails.length }} cocktails</span>
        <button class="add" @click="ajouter">+ Ajouter un cocktail</button>
      </div>
      <div v-for="c in cocktails" :key="c.id" class="row">
        <img :src="imageUrl(c.imageUrl)" :alt="c.nom" />
        <div class="nm">{{ c.nom }}</div>
        <span class="cat">{{ c.categorie }}</span>
        <button class="ic ed" @click="editer(c)" title="Modifier">✏️</button>
        <button class="ic de" @click="supprimer(c)" title="Supprimer">🗑️</button>
      </div>
    </div>

    <div class="panneau">
      <!-- Choix du mode d'ajout -->
      <div v-if="mode === 'choix'" class="choix">
        <h3>Ajouter un cocktail</h3>
        <button class="opt" @click="mode = 'creer'">
          <span class="ico">✏️</span>
          <span><b>Créer de zéro</b><small>Nouveau cocktail entièrement personnalisé</small></span>
        </button>
        <button class="opt" @click="mode = 'import'">
          <span class="ico">🍸</span>
          <span><b>Importer du catalogue</b><small>Choisir un vrai cocktail (TheCocktailDB)</small></span>
        </button>
        <button class="btn ghost" @click="mode = 'aucun'">Annuler</button>
      </div>

      <FormulaireCocktail v-else-if="mode === 'creer'" @saved="termine" @cancel="mode = 'aucun'" />
      <FormulaireCocktail v-else-if="mode === 'editer'" :cocktail="cocktailEdite" @saved="termine" @cancel="mode = 'aucun'" />
      <ImportCocktail v-else-if="mode === 'import'" @imported="termine" @cancel="mode = 'aucun'" />

      <div v-else class="vide">← Sélectionne un cocktail à modifier, ou ajoute-en un.</div>
    </div>
  </div>
</template>

<style scoped>
.wrap { display: flex; gap: 20px; align-items: flex-start; }
.liste { flex: 1.3; background: #fff; border-radius: 20px; box-shadow: var(--shadow-sm); overflow: hidden; }
.head { display: flex; justify-content: space-between; align-items: center; padding: 14px 18px; border-bottom: 1px solid var(--line); }
.compte { font-size: 13px; color: var(--ink-soft); font-weight: 600; }
.add { background: linear-gradient(135deg, var(--coral), var(--mango)); color: #fff; font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 13px; padding: 10px 14px; border-radius: 12px; }
.row { display: flex; align-items: center; gap: 12px; padding: 12px 18px; border-bottom: 1px solid var(--line); }
.row:last-child { border-bottom: none; }
.row img { width: 42px; height: 42px; border-radius: 12px; object-fit: cover; }
.row .nm { flex: 1; font-family: 'Poppins', sans-serif; font-weight: 600; font-size: 15px; }
.cat { font-size: 12px; color: var(--ink-soft); background: var(--cream); padding: 5px 10px; border-radius: 999px; }
.ic { width: 34px; height: 34px; border-radius: 10px; }
.ed { background: #eadfff; }
.de { background: #ffe4ea; }
.panneau { flex: 1; }
.choix { background: #fff; border-radius: 20px; box-shadow: var(--shadow-sm); padding: 20px; }
.choix h3 { font-size: 16px; font-weight: 700; margin-bottom: 14px; }
.opt { display: flex; align-items: center; gap: 14px; width: 100%; text-align: left; background: var(--cream); border: 1.5px solid var(--line); border-radius: 16px; padding: 16px; margin-bottom: 12px; }
.opt .ico { font-size: 26px; }
.opt b { display: block; font-family: 'Poppins', sans-serif; font-size: 15px; }
.opt small { color: var(--ink-soft); font-size: 12px; }
.vide { background: #fff; border-radius: 20px; box-shadow: var(--shadow-sm); padding: 40px 20px; text-align: center; color: var(--ink-soft); font-size: 14px; }
</style>
