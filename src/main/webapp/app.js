const API = "/api/todos";

const listEl = document.getElementById("list");
const formEl = document.getElementById("newTodoForm");
const titleEl = document.getElementById("title");

async function fetchJSON(url, opts) {
  const res = await fetch(url, opts);
  if (!res.ok) throw new Error(await res.text());
  return res.status === 204 ? null : res.json();
}

async function load() {
  const todos = await fetchJSON(API);
  listEl.innerHTML = "";
  for (const t of todos) {
    const li = document.createElement("li");
    li.className = t.done ? "done" : "";
    li.innerHTML = `
      <span>${escapeHtml(t.title)}</span>
      <div class="actions">
        <button data-id="${t.id}" class="toggle">${t.done ? "Undo" : "Done"}</button>
        <button data-id="${t.id}" class="delete">Delete</button>
      </div>`;
    listEl.appendChild(li);
  }
}

function escapeHtml(s){
  return s.replace(/[&<>\"]/g, c => ({"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;"}[c]));
}

formEl.addEventListener("submit", async (e) => {
  e.preventDefault();
  const title = titleEl.value.trim();
  if (!title) return;
  await fetchJSON(API, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ title })
  });
  titleEl.value = "";
  await load();
});

listEl.addEventListener("click", async (e) => {
  const id = e.target?.dataset?.id;
  if (!id) return;
  if (e.target.classList.contains("toggle")) {
    await fetchJSON(`${API}/${id}/toggle`, { method: "POST" });
  } else if (e.target.classList.contains("delete")) {
    await fetchJSON(`${API}/${id}`, { method: "DELETE" });
  }
  await load();
});

load().catch(err => alert(err.message));
