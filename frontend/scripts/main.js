/*
    Spring 2026 IS 241.210 - Team MO HealthVerify
    Written by Davis Ly
 */

(function () {
    const LENS_SIZE = 180;
    const ZOOM_LEVEL = 1.8;
    const PRIORITY_SELECTOR = ".top-link, a, h1, h2, h3, h4, h5, h6, p, label, span, button, li, td, th, input, textarea, select, img";
    const TEXT_TAGS = new Set(["a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "label", "span", "button", "li", "td", "th"]);

    let lensButton;
    let lens;
    let lensContent;
    let isMagnifierOn = false;
    let latestMouseEvent = null;
    let frameRequested = false;

    function createMagnifierUi() {
        lensButton = document.getElementById("magnifierToggle");
        if (!lensButton) {
            lensButton = document.createElement("button");
            lensButton.type = "button";
            lensButton.id = "magnifierToggle";
            lensButton.className = "magnifier-toggle";
            lensButton.setAttribute("aria-pressed", "false");
            lensButton.setAttribute("aria-label", "Toggle magnifier");
            lensButton.title = "Toggle magnifier";
            lensButton.textContent = "Magnifier";
            document.body.appendChild(lensButton);
        }

        lens = document.getElementById("magnifierLens");
        if (!lens) {
            lens = document.createElement("div");
            lens.id = "magnifierLens";
            lens.className = "magnifier-lens";
            lens.style.width = LENS_SIZE + "px";
            lens.style.height = LENS_SIZE + "px";

            lensContent = document.createElement("div");
            lensContent.className = "magnifier-content";
            lens.appendChild(lensContent);
            document.body.appendChild(lens);
        } else {
            lensContent = lens.querySelector(".magnifier-content");
        }
    }

    function canMagnifyElement(el) {
        if (!el || !el.tagName) return false;
        const tag = el.tagName.toLowerCase();
        if (tag === "script" || tag === "style" || tag === "html" || tag === "body") return false;
        if (el.id === "magnifierLens" || el.id === "magnifierToggle") return false;
        return true;
    }

    function toElement(node) {
        if (!node) return null;
        if (node.nodeType === Node.ELEMENT_NODE) return node;
        if (node.nodeType === Node.TEXT_NODE) return node.parentElement;
        return null;
    }

    function findBestTarget(e) {
        const direct = toElement(e.target);
        const pointed = toElement(document.elementFromPoint(e.clientX, e.clientY));
        const candidates = [direct, pointed];

        for (let i = 0; i < candidates.length; i++) {
            let candidate = candidates[i];
            if (!candidate) continue;

            if (candidate.closest) {
                const priority = candidate.closest(PRIORITY_SELECTOR);
                if (priority) candidate = priority;
            }

            while (candidate && !canMagnifyElement(candidate)) {
                candidate = candidate.parentElement;
            }
            if (!candidate) continue;

            const rect = candidate.getBoundingClientRect();
            if (rect.width > 0 && rect.height > 0) return candidate;
        }

        return null;
    }

    function buildTextPreviewNode(target) {
        const tag = target.tagName ? target.tagName.toLowerCase() : "";
        let text = "";

        if (tag === "input" || tag === "textarea") {
            text = target.value || target.placeholder || "";
        } else if (tag === "select") {
            text = target.options && target.selectedIndex >= 0 ? target.options[target.selectedIndex].text : "";
        } else if (TEXT_TAGS.has(tag)) {
            text = target.innerText || target.textContent || "";
        } else {
            text = target.textContent || "";
        }

        text = text.trim();
        if (!text) return null;

        const preview = document.createElement("div");
        preview.className = "magnifier-text-preview";
        preview.textContent = text;

        const styles = window.getComputedStyle(target);
        preview.style.font = styles.font;
        preview.style.fontFamily = styles.fontFamily;
        preview.style.color = styles.color;
        preview.style.backgroundColor = styles.backgroundColor;
        preview.style.padding = styles.padding;
        preview.style.border = styles.border;
        preview.style.borderRadius = styles.borderRadius;
        preview.style.whiteSpace = "pre-wrap";
        preview.style.display = "inline-block";
        preview.style.textDecoration = styles.textDecoration;
        preview.style.letterSpacing = styles.letterSpacing;

        return preview;
    }

    function hideLens() {
        if (!lens || !lensContent) return;
        lens.classList.remove("visible");
        lensContent.innerHTML = "";
    }

    function updateLensFromEvent(e) {
        if (!isMagnifierOn) {
            hideLens();
            return;
        }

        const target = findBestTarget(e);
        if (!target) {
            hideLens();
            return;
        }

        const rect = target.getBoundingClientRect();
        if (!rect.width || !rect.height) {
            hideLens();
            return;
        }

        let previewNode = buildTextPreviewNode(target);
        const isTextPreview = !!previewNode;
        if (!previewNode) {
            previewNode = target.cloneNode(true);
            previewNode.removeAttribute("id");
        }

        const offsetX = e.clientX - rect.left;
        const offsetY = e.clientY - rect.top;
        const lensRadius = LENS_SIZE / 2;

        lensContent.innerHTML = "";
        lensContent.appendChild(previewNode);
        lensContent.style.transformOrigin = "top left";

        if (isTextPreview) {
            const textRect = previewNode.getBoundingClientRect();
            const centeredX = lensRadius - (textRect.width * ZOOM_LEVEL) / 2;
            const centeredY = lensRadius - (textRect.height * ZOOM_LEVEL) / 2;
            lensContent.style.transform = "translate(" + centeredX + "px, " + centeredY + "px) scale(" + ZOOM_LEVEL + ")";
        } else {
            lensContent.style.transform =
                "translate(" +
                (lensRadius - offsetX * ZOOM_LEVEL) +
                "px, " +
                (lensRadius - offsetY * ZOOM_LEVEL) +
                "px) scale(" +
                ZOOM_LEVEL +
                ")";
        }

        lens.style.left = e.clientX - lensRadius + "px";
        lens.style.top = e.clientY - lensRadius + "px";
        lens.classList.add("visible");
    }

    function scheduleUpdate(e) {
        latestMouseEvent = e;
        if (frameRequested) return;

        frameRequested = true;
        window.requestAnimationFrame(function () {
            frameRequested = false;
            if (latestMouseEvent) updateLensFromEvent(latestMouseEvent);
        });
    }

    function toggleMagnifier() {
        isMagnifierOn = !isMagnifierOn;
        lensButton.setAttribute("aria-pressed", isMagnifierOn ? "true" : "false");
        lensButton.classList.toggle("active", isMagnifierOn);
        document.body.classList.toggle("magnifier-mode", isMagnifierOn);
        if (!isMagnifierOn) hideLens();
    }

    function init() {
        createMagnifierUi();
        lensButton.addEventListener("click", toggleMagnifier);

        document.addEventListener("mousemove", scheduleUpdate);
        document.addEventListener("mouseleave", hideLens);
        window.addEventListener("scroll", hideLens, {passive: true});
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", init);
    } else {
        init();
    }
})();
